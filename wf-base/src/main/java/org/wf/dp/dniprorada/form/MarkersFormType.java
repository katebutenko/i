package org.wf.dp.dniprorada.form;

import org.activiti.engine.form.AbstractFormType;

/**
 * Created by Dmytro Tsapko on 5/24/2015.
 */
public class MarkersFormType extends AbstractFormType {

    private static final long serialVersionUID = 1L;
    public static final String TYPE_NAME = "markers";

	//private final Logger log = LoggerFactory.getLogger(MarkersFormType.class);
    @Override
    public Object convertFormValueToModelValue(String propertyValue) {
		// throw new
        // ActivitiIllegalArgumentException("invalid action. Marker fields must have tag writable='false'");
        return propertyValue;
    }

    @Override
    public String convertModelValueToFormValue(Object modelValue) {
        if (modelValue == null) {
            return null;
        }
        return modelValue.toString();
    }

    @Override
    public String getName() {
        return TYPE_NAME;
    }

    public String getMimeType() {
        return "plain/text";
    }
}
