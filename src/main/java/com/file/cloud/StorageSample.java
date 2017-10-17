package com.file.cloud;

/**
 * Created by Osama on 3/9/2016.
 */
//[START all]
/*
 * Copyright (c) 2014 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.storage.Storage;
import com.google.api.services.storage.StorageScopes;
import com.google.api.services.storage.model.Bucket;
import com.google.api.services.storage.model.ObjectAccessControl;
import com.google.api.services.storage.model.Objects;
import com.google.api.services.storage.model.StorageObject;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Main class for the Cloud Storage JSON API sample.
 *
 * Demonstrates how to make an authenticated API call using the Google Cloud Storage API client
 * library for java, with Application Default Credentials.
 */
public class StorageSample {

    /**
     * Be sure to specify the name of your application. If the application name is {@code null} or
     * blank, the application will log a warning. Suggested format is "MyCompany-ProductName/1.0".
     */
    private static final String APPLICATION_NAME = "1217_osama_cloud";

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    // private static final String TEST_FILENAME = "json-test.txt";

    // [START get_service]
    private static Storage storageService;

    /**
     * Returns an authenticated Storage object used to make service calls to Cloud Storage.
     */
    private static Storage getService() throws IOException, GeneralSecurityException {


        if (null == storageService) {
            File file = new File("D:/google/OsamaCloud-5121d76f1d14.json");
            InputStream inputStream = new FileInputStream(file);
            GoogleCredential credential = GoogleCredential.fromStream(inputStream);
            // Depending on the environment that provides the default credentials (e.g. Compute Engine,
            // App Engine), the credentials may require us to specify the scopes we need explicitly.
            // Check for this case, and inject the Cloud Storage scope if required.
            if (credential.createScopedRequired()) {
                credential = credential.createScoped(StorageScopes.all());
            }
            HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            storageService = new Storage.Builder(httpTransport, JSON_FACTORY, credential)
                    .setApplicationName(APPLICATION_NAME).build();
        }
        return storageService;
    }
    // [END get_service]

    // [START list_bucket]
    /**
     * Fetch a list of the objects within the given bucket.
     *
     * @param bucketName the name of the bucket to list.
     * @return a list of the contents of the specified bucket.
     */
    public static List<StorageObject> listBucket(String bucketName)
            throws IOException, GeneralSecurityException {
        Storage client = getService();
        Storage.Objects.List listRequest = client.objects().list(bucketName);

        List<StorageObject> results = new ArrayList<StorageObject>();
        Objects objects;

        // Iterate through each page of results, and add them to our results list.
        do {
            objects = listRequest.execute();
            // Add the items in this page of results to the list we'll return.
            results.addAll(objects.getItems());

            // Get the next page, in the next iteration of this loop.
            listRequest.setPageToken(objects.getNextPageToken());
        } while (null != objects.getNextPageToken());

        return results;
    }
    // [END list_bucket]

    // [START get_bucket]
    /**
     * Fetches the metadata for the given bucket.
     *
     * @param bucketName the name of the bucket to get metadata about.
     * @return a Bucket containing the bucket's metadata.
     */
    public static Bucket getBucket(String bucketName) throws IOException, GeneralSecurityException {
        Storage client = getService();

        Storage.Buckets.Get bucketRequest = client.buckets().get(bucketName);
        // Fetch the full set of the bucket's properties (e.g. include the ACLs in the response)
        bucketRequest.setProjection("full");
        return bucketRequest.execute();
    }
    // [END get_bucket]

    // [START upload_stream]
    /**
     * Uploads data to an object in a bucket.
     *
     * @param file the name of the destination object.
     * @param contentType the MIME type of the data.

     * @param bucketName the name of the bucket to create the object in.
     */
    public static void uploadStream(File file, String contentType,String bucketName)
            throws IOException, GeneralSecurityException {
        InputStream stream=new FileInputStream(file);
        long fileLength=file.length();
        InputStreamContent contentStream =  new InputStreamContent(contentType,new BufferedInputStream(stream));
        contentStream.setLength(fileLength);
        StorageObject objectMetadata = new StorageObject()
                // Set the destination object name
                .setName(file.getName())
                // Set the access control list to publicly read-only
                .setAcl(Arrays.asList(
                        new ObjectAccessControl().setEntity("allUsers").setRole("READER")));


        // Do the insert
        Storage client = getService();
        Storage.Objects.Insert insertRequest = client.objects().insert(bucketName, objectMetadata, contentStream);
        insertRequest.execute();
    }
    // [END upload_stream]

    // [START delete_object]
    /**
     * Deletes an object in a bucket.
     *
     * @param path the path to the object to delete.
     * @param bucketName the bucket the object is contained in.
     */
    public static void deleteObject(String path, String bucketName)
            throws IOException, GeneralSecurityException {
        Storage client = getService();
        client.objects().delete(bucketName, path).execute();
    }
    // [END delete_object]


    /**
     * Exercises the class's functions - gets and lists a bucket, uploads and deletes an object.
     *
     * @param args the command-line arguments. The first argument should be the bucket name.
     */

    public void listStorageFiles(){

        String bucketName ="1217_osama_cloud";
        try {

            Bucket bucket = getBucket(bucketName);
            List<StorageObject> bucketContents = listBucket(bucketName);
            if (null == bucketContents) {
                System.out.println(
                        "There were no objects in the given bucket; try adding some and re-running.");
            }
            for (StorageObject object : bucketContents) {
                System.out.println("File: "+object.getName() +" LastUpdate: "+object.getUpdated().toString()+" Size(" + object.getSize() + " bytes)");
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {

    }
}
//[END all]
