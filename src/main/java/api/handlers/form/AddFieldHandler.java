package api.handlers.form;

import api.business.entities.Field;
import api.business.persistance.ISimpleEntityManager;
import api.business.services.interfaces.IFormService;
import api.contracts.base.BaseResponse;
import api.contracts.base.ErrorCodes;
import api.contracts.base.ErrorDto;
import api.contracts.form.AddFieldRequest;
import api.handlers.base.BaseHandler;
import api.helpers.validator.Validator;
import org.apache.shiro.SecurityUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;

@Stateless
public class AddFieldHandler extends BaseHandler<AddFieldRequest, BaseResponse> {
    @Inject
    private ISimpleEntityManager simpleEntityManager;
    @Inject
    private IFormService formService;

    @Override
    public ArrayList<ErrorDto> validate(AddFieldRequest request) {
        ArrayList<ErrorDto> authErrors = new Validator().isAdministrator().getErrors();

        if (!authErrors.isEmpty()) return authErrors;

        ArrayList<ErrorDto> errors = new Validator().allFieldsSet(request).getErrors();

        if (formService.getFieldByName(request.name) != null) {
            errors.add(new ErrorDto("Field with same name already exists", ErrorCodes.VALIDATION_ERROR));
        }
        if (request.name.isEmpty()) {
            errors.add(new ErrorDto("Name is required", ErrorCodes.VALIDATION_ERROR));
        }
        if (request.type.isEmpty()) {
            errors.add(new ErrorDto("Type is required", ErrorCodes.VALIDATION_ERROR));
        }
        if (request.description.isEmpty()) {
            errors.add(new ErrorDto("Description is required", ErrorCodes.VALIDATION_ERROR));
        }
        return errors;
    }

    @Override
    public BaseResponse handleBase(AddFieldRequest request) {
        BaseResponse response = createResponse();
        Field field = new Field();
        field.setDescription(request.description);
        field.setName(request.name);
        field.setRequired(request.required);
        field.setVisible(request.visible);
        field.setValidationRegex(request.validationRegex.isEmpty() ? null : request.validationRegex);
        field.setType(request.type);
        simpleEntityManager.insert(field);
        return response;
    }

    @Override
    public BaseResponse createResponse() {
        return new BaseResponse();
    }
}
