package com.project.Entity;

import com.project.helper.VerificationType;
import lombok.Data;


@Data
public class TwoFactorAuth {

            private boolean isEnabled=false;

            private VerificationType verificationType;

}
