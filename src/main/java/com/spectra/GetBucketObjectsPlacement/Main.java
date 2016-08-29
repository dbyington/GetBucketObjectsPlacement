package com.spectra.GetBucketObjectsPlacement;

import com.spectralogic.ds3client.Ds3Client;
import com.spectralogic.ds3client.Ds3ClientBuilder;
import com.spectralogic.ds3client.commands.GetBucketRequest;
import com.spectralogic.ds3client.commands.GetServiceRequest;
import com.spectralogic.ds3client.commands.GetServiceResponse;
import com.spectralogic.ds3client.commands.spectrads3.*;
import com.spectralogic.ds3client.helpers.Ds3ClientHelpers;
import com.spectralogic.ds3client.models.*;
import com.spectralogic.ds3client.models.bulk.Ds3Object;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Main {
    public static void main(final String[] args) throws IOException {

        final Ds3Client ds3Client = Ds3ClientBuilder.fromEnv().withHttps(false).build();

        final GetBucketSpectraS3Response myBucket = ds3Client.getBucketSpectraS3(new GetBucketSpectraS3Request(args[0]));
        final UUID myBucketUUID = myBucket.getBucketResult().getId();

        System.out.println(myBucket.getBucketResult().getId());

        System.out.println("Getting objects");

        final GetObjectsWithFullDetailsSpectraS3Response bucketObjects = ds3Client.getObjectsWithFullDetailsSpectraS3(new GetObjectsWithFullDetailsSpectraS3Request().withBucketId(args[0]));



        System.out.println("done");




    }
}


