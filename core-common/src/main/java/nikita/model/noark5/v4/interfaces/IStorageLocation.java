package nikita.model.noark5.v4.interfaces;

import nikita.model.noark5.v4.secondary.StorageLocation;

import java.util.List;
/**
 * Created by tsodring on 12/7/16.
 */
public interface IStorageLocation {
    List<StorageLocation> getReferenceStorageLocation();

    void setReferenceStorageLocation(List<StorageLocation> storageLocations);
}
