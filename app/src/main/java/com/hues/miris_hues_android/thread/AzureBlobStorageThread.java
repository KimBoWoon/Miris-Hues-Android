package com.hues.miris_hues_android.thread;

import android.content.Context;

import com.hues.miris_hues_android.data.Constant;
import com.hues.miris_hues_android.data.DataManager;
import com.hues.miris_hues_android.data.SharedStore;
import com.hues.miris_hues_android.log.Logging;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.ListBlobItem;

/**
 * Created by 보운 on 2017-02-05.
 */

public class AzureBlobStorageThread extends Thread {
    private String url;

    public AzureBlobStorageThread(String url) {
        this.url = url;
    }

    @Override
    public void run() {
        super.run();

        try {
            // Retrieve storage account from connection-string.
//            CloudStorageAccount storageAccount = CloudStorageAccount.parse(url);
            CloudStorageAccount storageAccount = CloudStorageAccount.parse("DefaultEndpointsProtocol=http;AccountName=miris;AccountKey=Oki2fkesXIPsAKQlxJdmJIFQI+r4WP1TYXXs8UWM1nMNbdLvkwPwmsgBgWGc2LQnyk9GVzfaIT4kNSL064968A==");
            // Create the blob client.
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

            // Retrieve reference to a previously created container.
            CloudBlobContainer container = blobClient.getContainerReference("images");

            Logging.i(String.valueOf(container.listBlobs()));

            // Loop over blobs within the container and output the URI to each of them.
            for (ListBlobItem blobItem : container.listBlobs()) {
                DataManager.getInstance().getListBlobItems().add(blobItem);
            }
        } catch (Exception e) {
            // Output the stack trace.
            e.printStackTrace();
        }
    }
}
