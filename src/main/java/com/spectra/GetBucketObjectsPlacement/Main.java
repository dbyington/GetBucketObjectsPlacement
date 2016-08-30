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
        System.out.println("Getting black pearl file list");
        final Iterable<Contents> bpfilelist = clientHelpers.listObjects(args[0]);
        final Iterable<Ds3Object> bpDs3Objects = clientHelpers.toDs3Iterable(bpfilelist);

        System.out.println(bpDs3Objects.toString().length());
        final List<Ds3Object> objectList = Lists.newArrayList(bpDs3Objects);

        final GetBucketSpectraS3Response myBucket = ds3Client.getBucketSpectraS3(new GetBucketSpectraS3Request(args[0]));
        System.out.printf("Bucket name: %s\tBucket UUID: %s\n", myBucket.getBucketResult().getName(),myBucket.getBucketResult().getId());
        System.out.printf("Getting %f objects\n", bpDs3Objects.toString().length());


        final GetPhysicalPlacementForObjectsWithFullDetailsSpectraS3Response phyicalPlacementResponse = ds3Client.getPhysicalPlacementForObjectsWithFullDetailsSpectraS3(new GetPhysicalPlacementForObjectsWithFullDetailsSpectraS3Request(args[0], objectList));

        for (BulkObject obj : phyicalPlacementResponse.getBulkObjectListResult().getObjects()) {
            System.out.printf("Object: %s\tUUID: %s\tTapes:", obj.getName(), obj.getId());

            for (Tape tape : obj.getPhysicalPlacement().getTapes()) {
                System.out.printf(" %s",tape.getBarCode());
            }
            System.out.printf("\n");
        }
        System.out.println("done");




    }


}


