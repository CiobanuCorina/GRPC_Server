package com.company;

import com.company.Services.MessageService;
import com.company.utils.PropertiesObtainer;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GrpcServer {
    public static void main(String[] args) {
        try {
            int port = Integer.parseInt(PropertiesObtainer.getProperties().getProperty("port"));
            Server server = ServerBuilder.forPort(port).addService(new MessageService()).build();
            server.start();
            System.out.println("Server started at" + server.getPort());
            server.awaitTermination();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
