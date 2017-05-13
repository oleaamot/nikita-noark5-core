package nikita.model.noark5.v4.interfaces;

import nikita.model.noark5.v4.secondary.StorageLocation;

import java.util.Set;
/**
 * Created by tsodring on 12/7/16.
 */
public interface IStorageLocation {
    Set<StorageLocation> getReferenceStorageLocation();
    void setReferenceStorageLocation(Set<StorageLocation> storageLocations);
}
