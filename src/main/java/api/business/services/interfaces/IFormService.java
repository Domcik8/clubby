package api.business.services.interfaces;

import api.business.entities.Field;
import api.business.entities.FormResult;
import api.business.entities.User;
import api.contracts.dto.FormInfoDto;
import api.contracts.dto.SubmitFormDto;

import java.util.List;

public interface IFormService {

    List<Field> getForm();

    Field getFieldByName(String name);

    List<FormResult> getMyFields();

    List<Field> getVisibleFields();

    List<FormInfoDto> getFormByUserId(int id);

    FormResult getFormResult(String fieldName, int userId);

    void saveFormResults(List<SubmitFormDto> formDtos, User user);
}
