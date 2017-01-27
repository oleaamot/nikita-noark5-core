package nikita.model.noark5.v4.interfaces;

import java.util.Set;
import nikita.model.noark5.v4.StorageLocation;
/**
 * Created by tsodring on 12/7/16.
 */
public interface IStorageLocation {
    Set<StorageLocation> getReferenceStorageLocation();
    void setReferenceStorageLocation(Set<StorageLocation> storageLocations);
}
