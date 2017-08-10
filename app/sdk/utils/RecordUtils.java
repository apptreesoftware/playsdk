package sdk.utils;

import sdk.converter.ObjectConverter;
import sdk.converter.attachment.AbstractAttachment;
import sdk.converter.attachment.Attachment;
import sdk.data.DataSetItemAttachment;
import sdk.data.Record;

import java.util.List;

/**
 * Created by Orozco on 8/10/17.
 */
public class RecordUtils {


    public static void copySingleAttachmentToRecordForIndex(Object object, Record record, int index) {
        DataSetItemAttachment attachmentRecord = record.addNewDataSetItemAttachment(index);
        ObjectConverter.copyToAttachment(attachmentRecord, object);
    }

    public static void copyListOfAttachmentsToRecordForIndex(List<Object> objects, Record record, int index) {
        for (Object object : objects) {
            DataSetItemAttachment attachmentRecord = record.addNewDataSetItemAttachment(index);
            ObjectConverter.copyToAttachment(attachmentRecord, object);
        }
    }

    public static void copyListOfAttachmentsFromRecordForIndex(List<DataSetItemAttachment> attachments, List<AbstractAttachment> objects) {
            for(DataSetItemAttachment attachment:attachments) {
                AbstractAttachment apptreeAttachment = new Attachment();
                ObjectConverter.copyFromAttachment(attachment, apptreeAttachment);
                objects.add(apptreeAttachment);
            }
    }

    public static void copyAttachmentFromRecordForIndex(List<DataSetItemAttachment> attachments, AbstractAttachment object) {
            DataSetItemAttachment attachment = attachments.get(0);
            if(attachment == null) return;
            ObjectConverter.copyFromAttachment(attachment, object);
    }










}
