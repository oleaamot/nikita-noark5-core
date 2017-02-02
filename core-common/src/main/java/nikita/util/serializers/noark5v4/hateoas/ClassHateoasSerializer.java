package nikita.util.serializers.noark5v4.hateoas;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.google.common.collect.Iterables;
import nikita.model.noark5.v4.Class;
import nikita.model.noark5.v4.hateoas.ClassHateoas;
import nikita.util.CommonUtils;

import java.io.IOException;
import java.lang.reflect.Type;

import static nikita.config.N5ResourceMappings.CLASS;

/**
 *
 * Serialise an outgoing Class object as JSON.
 *
 * Having an own serializer is done to have more fine grained control over the output. We need to be able to especially
 * control the HATEOAS links and the actual format of the HATEOAS links might change over time with the standard. This
 * allows us to be able to easily adapt to any changes
 *
 * Only Norwegian property names are used on the outgoing JSON property names and are in accordance with the Noark
 * standard.
 *
 * Note. Only values that are part of the standard are included in the JSON. Properties like 'id' or 'deleted' are not
 * exported
 *
 */
public class ClassHateoasSerializer extends StdSerializer<ClassHateoas> {

    public ClassHateoasSerializer() {
        super(ClassHateoas.class);
    }

    @Override
    public void serialize(ClassHateoas classHateoas, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {

        Iterable<Class> classIterable = classHateoas.getClassIterable();
        if (classIterable != null && Iterables.size(classIterable) > 0) {
            jgen.writeStartObject();
            jgen.writeFieldName(CLASS);
            jgen.writeStartArray();
            for (Class klass : classIterable) {
                serializeClass(klass, classHateoas, jgen, provider);
            }
            jgen.writeEndArray();
            CommonUtils.Hateoas.Serialize.printHateoasLinks(jgen, classHateoas.getLinks());
            jgen.writeEndObject();
        } else if (classHateoas.getKlass() != null) {
            serializeClass(classHateoas.getKlass(), classHateoas, jgen, provider);
        }
        // It's an empty object, so returning empty Hateoas links _links : []
        else {
            jgen.writeStartObject();
            CommonUtils.Hateoas.Serialize.printHateoasLinks(jgen, null);
            jgen.writeEndObject();
        }
    }

    private void serializeClass(Class klass, ClassHateoas classHateoas,
                                JsonGenerator jgen, SerializerProvider provider) throws IOException {


        jgen.writeStartObject();
        CommonUtils.Hateoas.Serialize.printSystemIdEntity(jgen, klass);
        CommonUtils.Hateoas.Serialize.printTitleAndDescription(jgen, klass);
        CommonUtils.Hateoas.Serialize.printCreateEntity(jgen, klass);
        CommonUtils.Hateoas.Serialize.printFinaliseEntity(jgen, klass);
        // TODO: Fix this ! CommonCommonUtils.Hateoas.Serialize.printCrossReference(jgen, klass);
        CommonUtils.Hateoas.Serialize.printDisposal(jgen, klass);
        CommonUtils.Hateoas.Serialize.printScreening(jgen, klass);
        CommonUtils.Hateoas.Serialize.printClassified(jgen, klass);
        CommonUtils.Hateoas.Serialize.printHateoasLinks(jgen, classHateoas.getLinks());
        jgen.writeEndObject();
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
        throw new UnsupportedOperationException();
    }
}
