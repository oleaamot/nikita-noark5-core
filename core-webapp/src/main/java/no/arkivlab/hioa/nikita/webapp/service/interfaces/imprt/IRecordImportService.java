package no.arkivlab.hioa.nikita.webapp.service.interfaces.imprt;


import nikita.model.noark5.v4.DocumentDescription;
import nikita.model.noark5.v4.Record;

public interface IRecordImportService {

	// -- All CREATE operations

	Record save(Record record);
	DocumentDescription createDocumentDescriptionAssociatedWithRecord(String recordSystemId,
																	  DocumentDescription documentDescription);
}
