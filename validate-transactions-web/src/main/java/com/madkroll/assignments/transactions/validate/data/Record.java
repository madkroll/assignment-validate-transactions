package com.madkroll.assignments.transactions.validate.data;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@XmlAccessorType(XmlAccessType.FIELD)
public class Record {

    @XmlAttribute(name = "reference", required = true)
    private final long reference;

    @XmlElement(name = "accountNumber", required = true)
    private final String accountNumber;

    @XmlElement(name = "description", required = true)
    private final String description;

    @XmlElement(name = "startBalance", required = true)
    private final BigDecimal startBalance;

    @XmlElement(name = "mutation", required = true)
    private final BigDecimal mutation;

    @XmlElement(name = "endBalance", required = true)
    private final BigDecimal endBalance;

}
