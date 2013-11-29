/*
* Copyright 2010-2013, the original author or authors
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package localdomain.localhost.domain;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;

public class WsChatServlet extends WebSocketServlet{
    private static final long serialVersionUID = 1L;
    private static ArrayList<MyMessageInbound> mmiList = new ArrayList<MyMessageInbound>();

    @Override
    protected StreamInbound createWebSocketInbound(String s, HttpServletRequest httpServletRequest) {
        return new MyMessageInbound();
    }

    private class MyMessageInbound extends MessageInbound{
        WsOutbound myoutbound;

        @Override
        public void onOpen(WsOutbound outbound){
            try {
                System.out.println("Client Arrive");
                this.myoutbound = outbound;
                outbound.writeTextMessage(CharBuffer.wrap("Hi, this is a simple echo Websocket server running on CloudBees!"));
                mmiList.add(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onClose(int status){
            System.out.println("Client Leaves");
            sendTextMessage("Bye!");
            mmiList.remove(this);
        }

        @Override
        public void onTextMessage(CharBuffer cb) throws IOException{
            System.out.println("Accept Message : "+ cb);
            for(MyMessageInbound mmib: mmiList){
                CharBuffer buffer = CharBuffer.wrap(cb);
                mmib.myoutbound.writeTextMessage(buffer);
                mmib.myoutbound.flush();
            }
        }

        @Override
        public void onBinaryMessage(ByteBuffer bb) throws IOException{
        }

        public synchronized void sendTextMessage(String message) {
            try {
                CharBuffer buffer = CharBuffer.wrap(message);
                myoutbound.writeTextMessage(buffer);
                myoutbound.flush();

            } catch (IOException e) {
                System.out.println("Exception sending the message");
            }
        }
    }
}