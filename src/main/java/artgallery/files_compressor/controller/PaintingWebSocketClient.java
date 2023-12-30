package artgallery.files_compressor.controller;

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

import java.lang.reflect.Type;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PaintingWebSocketClient {

  @Value("app.paintings.client.server-urls")
  private List<String> serverUrls;

  @EventListener(ContextRefreshedEvent.class)
  public void connect() throws InterruptedException {
    for (String url : serverUrls) {
      WebSocketClient client = new StandardWebSocketClient();

      WebSocketStompClient stompClient = new WebSocketStompClient(client);
      stompClient.setMessageConverter(new MappingJackson2MessageConverter());

      StompSessionHandler sessionHandler = new CustomStompSessionHandler();
      var future = stompClient.connectAsync(url, sessionHandler);
      future.thenAccept(result -> {
        log.info("future accept: " + result.toString());
      });
    }
  }

  private static class CustomStompSessionHandler extends StompSessionHandlerAdapter {
    @Override
    public Type getPayloadType(StompHeaders headers) {
      log.info("get payload type " + headers.toString());
      return super.getPayloadType(headers);
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
      log.info("handle frame " + headers.toString() + ", " + payload);
      super.handleFrame(headers, payload);
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
      log.info("after connected " + session.toString() + ", " + connectedHeaders);
      super.afterConnected(session, connectedHeaders);
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
      log.info("handle exception " + session.toString());
      super.handleException(session, command, headers, payload, exception);
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
      log.info("handle transport error " + session.toString());
      super.handleTransportError(session, exception);
    }
  }
}
