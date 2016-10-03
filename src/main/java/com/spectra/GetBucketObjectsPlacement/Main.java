package com.spectra.GetBucketObjectsPlacement;

import com.google.common.collect.Lists;
import com.spectralogic.ds3client.Ds3Client;
import com.spectralogic.ds3client.Ds3ClientBuilder;
import com.spectralogic.ds3client.commands.spectrads3.*;
import com.spectralogic.ds3client.helpers.Ds3ClientHelpers;
import com.spectralogic.ds3client.models.*;
import com.spectralogic.ds3client.models.bulk.Ds3Object;

import java.io.IOException;
import java.util.*;

public class Main {


    public static void main(final String[] args) throws IOException {

        final Ds3Client ds3Client = Ds3ClientBuilder.fromEnv().withHttps(false).build();
        final Ds3ClientHelpers clientHelpers = Ds3ClientHelpers.wrap(ds3Client);

        clientHelpers.ensureBucketExists(args[0]);

        final GetBucketSpectraS3Response myBucket = ds3Client.getBucketSpectraS3(new GetBucketSpectraS3Request(args[0]));
        System.out.printf("Bucket name: %s\tBucket UUID: %s\n", myBucket.getBucketResult().getName(),myBucket.getBucketResult().getId());

        final GetObjectsWithFullDetailsSpectraS3Request detailedListRequest = new GetObjectsWithFullDetailsSpectraS3Request();
        detailedListRequest.withBucketId(args[0]);
        detailedListRequest.withIncludePhysicalPlacement(true);

        final GetObjectsWithFullDetailsSpectraS3Response detailedList = ds3Client.getObjectsWithFullDetailsSpectraS3(detailedListRequest);


        for (DetailedS3Object detailedS3Object : detailedList.getDetailedS3ObjectListResult().getDetailedS3Objects()) {
            System.out.printf("Object: %-60s Tape: %-8s\n", detailedS3Object.getName(), detailedS3Object.getBlobs().getObjects().get(0).getPhysicalPlacement().getTapes().get(0).getBarCode());
        }


        System.out.println("done");

    }


}


