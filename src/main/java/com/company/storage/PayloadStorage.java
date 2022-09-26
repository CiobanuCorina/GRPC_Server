package com.company.storage;

import java.util.concurrent.ConcurrentLinkedQueue;

public class PayloadStorage {
    private static volatile PayloadStorage instance;
    public ConcurrentLinkedQueue<Payload> payloadData;

    public PayloadStorage()
    {
        payloadData = new ConcurrentLinkedQueue<Payload>();
    }

    public void addPayloadData(Payload payload)
    {
        payloadData.add(payload);
    }

    public ConcurrentLinkedQueue<Payload> getPayloadData() {
        return payloadData;
    }

    public boolean isEmpty()
    {
        return payloadData.isEmpty();
    }

    public static PayloadStorage getInstance() {
        PayloadStorage result = instance;
        if (result != null) {
            return result;
        }
        synchronized (PayloadStorage.class) {
            if (instance == null) {
                instance = new PayloadStorage();
            }
            return instance;
        }
    }
}
