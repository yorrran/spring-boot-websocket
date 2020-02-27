package com.example.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint(value = "/ws/asset")
@Component
public class WebSocketServer {
  @PostConstruct
  public void init() {
    System.out.println("websocket loaded");
  }
  private static Logger log = LoggerFactory.getLogger(WebSocketServer.class);
  private static final AtomicInteger OnlineCount = new AtomicInteger(0);
  private static CopyOnWriteArraySet<Session> SessionSet = new CopyOnWriteArraySet<Session>();


  @OnOpen
  public void onOpen(Session session) {
    SessionSet.add(session);
    int cnt = OnlineCount.incrementAndGet();
    log.info("connection, {}", cnt,session.getId());
    SendMessage(session, "connect successfully");
  }

  /**
   * 连接关闭调用的方法
   */
  @OnClose
  public void onClose(Session session) {
    SessionSet.remove(session);
    int cnt = OnlineCount.decrementAndGet();
    log.info("connection closed: ", cnt);
  }


  @OnMessage
  public void onMessage(String message, Session session) {
    log.info("from client, {}", message);
    System.out.println("message:"+message);
    SendMessage(session, "receive message："+message);

  }

  @OnError
  public void onError(Session session, Throwable error) {
    log.error("error，session id",error.getMessage(),session.getId());
    error.printStackTrace();
  }


  public static void SendMessage(Session session, String message) {
    try {
      session.getBasicRemote().sendText(message);
    } catch (IOException e) {
      log.error("error in sending message", e.getMessage());
      e.printStackTrace();
    }
  }


  public static void BroadCastInfo(String message) throws IOException {
    for (Session session : SessionSet) {
      if(session.isOpen()){
        SendMessage(session, message);
      }
    }
  }


  public static void SendMessage(String message,String sessionId) throws IOException {
    Session session = null;
    for (Session s : SessionSet) {
      if(s.getId().equals(sessionId)){
        session = s;
        break;
      }
    }
    if(session!=null){
      SendMessage(session, message);
    }
    else{
      log.warn("did not find session id：{}",sessionId);
    }
  }

}
