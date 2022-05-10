package mk.finki.lm.customValidation;

import lombok.SneakyThrows;
import org.passay.*;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.StringJoiner;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword,String> {
    @Override
    public void initialize(ValidPassword constraintAnnotation) {

    }


    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        Properties props = new Properties();
        InputStream inputStream = getClass()
                .getClassLoader().getResourceAsStream("passay.properties");
        try {
            props.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MessageResolver resolver = new PropertiesMessageResolver(props);

        PasswordValidator passwordValidator = new PasswordValidator(resolver,Arrays.asList(
                new CharacterRule(EnglishCharacterData.UpperCase,1),
                new CharacterRule(EnglishCharacterData.LowerCase,1),
                new CharacterRule(EnglishCharacterData.Digit,1),
                new CharacterRule(EnglishCharacterData.Special,1)
                ));

        RuleResult result = passwordValidator.validate(new PasswordData(s));
        if (result.isValid()){
            return true;
        }
        List<String> messages = passwordValidator.getMessages(result);
        String messageTemplate = String.join(",", messages);
        constraintValidatorContext.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();

        return false;
    }
}
