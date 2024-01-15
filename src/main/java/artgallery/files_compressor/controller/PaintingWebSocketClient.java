package artgallery.files_compressor.controller;

import artgallery.files_compressor.model.ImageCompressionRequest;
import artgallery.files_compressor.model.ImageCompressionResponse;
import artgallery.files_compressor.service.PaintingCompressionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.io.IOException;
import java.lang.reflect.Type;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PaintingWebSocketClient {
  private final PaintingCompressionService paintingCompressionService;

  @Value("${app.paintings.client.server-urls}")
  private String[] serverUrls;

  @EventListener(ContextRefreshedEvent.class)
  public void connect() {
    for (String url : serverUrls) {
      WebSocketClient client = new StandardWebSocketClient();

      WebSocketStompClient stompClient = new WebSocketStompClient(client);
      stompClient.setMessageConverter(new MappingJackson2MessageConverter());

      StompSessionHandler sessionHandler = new CustomStompSessionHandler(paintingCompressionService);
      var future = stompClient.connectAsync(url, sessionHandler);
      future.thenAccept(result -> {
        log.info("future accept: " + result.toString());
      });
    }
  }

  private static class CustomStompSessionHandler extends StompSessionHandlerAdapter {
    private final PaintingCompressionService paintingCompressionService;
    private StompSession stompSession;

    public CustomStompSessionHandler(PaintingCompressionService paintingCompressionService) {
      this.paintingCompressionService = paintingCompressionService;
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
      return ImageCompressionRequest.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object _payload) {
      if (_payload instanceof ImageCompressionRequest payload) {
        ImageCompressionResponse response;
        try {
          var result = paintingCompressionService.compressImage(payload);
          response = new ImageCompressionResponse(result.getSource(), result.getDestination(),
            result.getMimeType(), true, null);
        } catch (Exception ex) {
          String msg = ex.getClass().getName() + ": " + ex.getMessage();
          response = new ImageCompressionResponse(payload.getSource(), payload.getDestination(),
            payload.getMimeType(), false, msg);
          log.warn(msg);
        }
        stompSession.send("/app/pictures.compression", response);
      }
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
      log.info("new session established: " + session.getSessionId());
      session.subscribe("/user/queue/pictures.compression", this);
      log.info("subscribed");
      this.stompSession = session;
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
      log.warn("handle exception: " + exception.getMessage()
        + ", " + session.toString() + ", payload" + new String(payload));
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
      log.error("transport error: " + session.toString() + ", " + exception.getMessage());
    }
  }
}
