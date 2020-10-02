package com.madkroll.assignments.transactions.validate.data;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@XmlRootElement(name = "records")
@XmlAccessorType(XmlAccessType.FIELD)
public class Records {

    @XmlElement(name = "record")
    private final List<Record> records;

}
