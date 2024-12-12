package com.hibe.entity;

import com.hibe.converter.BirthdayConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class PersonalInfo {
    private String firstname;

    private String lastname;

    @Column(name = "birth_date")
    @Convert(converter = BirthdayConverter.class)
    private Birthday birthday;
}
