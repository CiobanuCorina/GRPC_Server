package com.company.Services;

import com.company.storage.Payload;
import com.company.storage.PayloadStorage;
import com.company.stubs.Message;
import com.company.stubs.messageBrokerGrpc;
import com.company.utils.MessageTypes;
import com.company.utils.PropertiesObtainer;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

public class MessageService extends messageBrokerGrpc.messageBrokerImplBase{
    private final PayloadStorage payloadStorage = PayloadStorage.getInstance();


    public void sendMessage(Payload payload) throws IOException {
        for(Payload payloadData : payloadStorage.getPayloadData()) {
            if(payloadData.getTopic().equals(payload.getTopic())) {
                ManagedChannel channel = ManagedChannelBuilder
                        .forAddress(PropertiesObtainer.getProperties().getProperty("address"),
                                Integer.parseInt(PropertiesObtainer.getProperties().getProperty("port")))
                        .usePlaintext()
                        .build();
                messageBrokerGrpc.messageBrokerStub stub =messageBrokerGrpc.newStub(channel);
                Message.APIResponse.Builder requestBuilder = Message.APIResponse.newBuilder();
                if (requestBuilder.getResponseMessage().equals("Success"))
                    System.out.println("Message was send");
            }
        }
    }

    @Override
    public void receiveMessage(Message.MessageRequest request, StreamObserver<Message.APIResponse> responseObserver) {
        Payload payload = new Payload(request.getTopic(), request.getType(), request.getMessage());

        Message.APIResponse.Builder responseBuilder = Message.APIResponse.newBuilder();

        if(payload.getType().equals(MessageTypes.CONNECT.name())) {
            payloadStorage.addPayloadData(payload);
            this.sendSuccessMessage(responseBuilder);
        } else if(payload.getType().equals(MessageTypes.MESSAGE.name())) {
            try {
                this.sendMessage(payload);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            responseBuilder.setResponseCode(100).setResponseMessage("Invalid message type");
        }

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    public void sendSuccessMessage(Message.APIResponse.Builder response) {
        response.setResponseCode(0).setResponseMessage("Success");
    }
}
