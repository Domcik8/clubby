package api.handlers.users;

import api.contracts.requests.HasPermissionRequest;
import api.contracts.responses.HasPermissionResponse;
import api.contracts.responses.base.ErrorCodes;
import api.contracts.responses.base.ErrorDto;
import api.handlers.base.BaseHandler;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.util.ArrayList;

public class HasPermissionHandler extends BaseHandler<HasPermissionRequest, HasPermissionResponse> {
    @Override
    protected ArrayList<ErrorDto> validate(HasPermissionRequest request) {
        Subject currentUser = SecurityUtils.getSubject();

        ArrayList<ErrorDto> errors = new ArrayList<>();

        if (request == null) {
            errors.add(new ErrorDto("Request missing.", ErrorCodes.VALIDATION_ERROR));
        }

        if(request.PermissionName == null){
            errors.add(new ErrorDto("PermissionName missing.", ErrorCodes.VALIDATION_ERROR));
            return errors;
        }

        if(request.PermissionName.isEmpty()){
            errors.add(new ErrorDto("PermissionName empty.", ErrorCodes.VALIDATION_ERROR));
            return errors;
        }

        if (!currentUser.isAuthenticated()) {
            errors.add(new ErrorDto("Not authenticated.", ErrorCodes.AUTHENTICATION_ERROR));
        }

        return errors;
    }

    @Override
    protected HasPermissionResponse handleBase(HasPermissionRequest request) {
        Subject currentUser = SecurityUtils.getSubject();

        boolean hasPermission = currentUser.isPermitted(request.PermissionName);

        HasPermissionResponse response = createResponse();
        response.HasPermission = hasPermission;

        return response;
    }

    @Override
    protected HasPermissionResponse createResponse() {
        return new HasPermissionResponse();
    }
}