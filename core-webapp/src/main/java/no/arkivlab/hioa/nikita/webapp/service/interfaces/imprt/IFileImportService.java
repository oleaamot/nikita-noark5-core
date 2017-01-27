package no.arkivlab.hioa.nikita.webapp.service.interfaces.imprt;

import nikita.model.noark5.v4.BasicRecord;
import nikita.model.noark5.v4.File;
import nikita.model.noark5.v4.Record;
import nikita.util.exceptions.NikitaEntityNotFoundException;

public interface IFileImportService {
	// -- All CREATE operations
	File createFile(File file);
	Record createRecordAssociatedWithFile(String fileSystemId, Record record);
	BasicRecord createBasicRecordAssociatedWithFile(String fileSystemId, BasicRecord basicRecord);
}
