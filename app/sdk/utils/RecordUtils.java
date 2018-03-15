package sdk.utils;

import sdk.converter.AttributeProxy;
import sdk.converter.ObjectConverter;
import sdk.converter.attachment.ApptreeAttachment;
import sdk.converter.attachment.Attachment;
import sdk.data.DataSetItemAttachment;
import sdk.data.Record;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
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

    public static Collection<ApptreeAttachment> copyListOfAttachmentsFromRecordForIndex(List<DataSetItemAttachment> attachments, AttributeProxy proxy) throws
                                                                                                                                                       IllegalAccessException,
                                                                                                                                                       InstantiationException,
                                                                                                                                                       InvocationTargetException {
        Collection<ApptreeAttachment> newAttachments = new ArrayList<>();
        for (DataSetItemAttachment attachment : attachments) {
            ApptreeAttachment newAttachment = (ApptreeAttachment) proxy.getType().newInstance();
            ObjectConverter.copyFromAttachment(attachment, newAttachment);
            ObjectConverter.copyFromRecord(attachment, newAttachment);
            newAttachments.add(newAttachment);
        }
        return newAttachments;
    }

    public static void copyAttachmentFromRecordForIndex(List<DataSetItemAttachment> attachments, ApptreeAttachment object, AttributeProxy proxy) throws
                                                                                                                                                 InvocationTargetException,
                                                                                                                                                 IllegalAccessException {
        DataSetItemAttachment attachment = attachments.get(0);
        if (attachment == null) return;
        ObjectConverter.copyFromAttachment(attachment, object);
        ObjectConverter.copyFromRecord(attachment, object);
    }


}
