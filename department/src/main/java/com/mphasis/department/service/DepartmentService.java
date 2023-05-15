package com.mphasis.department.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.mphasis.department.entity.Department;
import com.mphasis.department.entity.DisplayInfoVO;
import com.mphasis.department.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    public Department saveDepartment(Department dep){
        return departmentRepository.save(dep);
    }

    public Department getDepartmentById(Long id){
        return departmentRepository.findById(id).get();
    }

    public List<Department> getDepartments(){
        return departmentRepository.findAll();
    }

    public DisplayInfoVO getDisplayMessageWithLocale(String language){
        Locale defaultLocale = new Locale.Builder().setLanguage("en").build();
        Locale newLocale = new Locale.Builder().setLanguage(language).build();

        ResourceBundle defaultLabels = ResourceBundle.getBundle("message", defaultLocale);
        String defaultMessage = defaultLabels.getString("welcome.message");

        ResourceBundle labels = ResourceBundle.getBundle("message", newLocale);
        String displayMessage = labels.getString("welcome.message");

        Long defaultLongNumber = 123456789L;

        DisplayInfoVO displayInfoVO = new DisplayInfoVO();

        displayInfoVO.setDisplayMessage(defaultMessage);
        displayInfoVO.setFormattedMessage(displayMessage);

        displayInfoVO.setDisplayNumber(defaultLongNumber);

        NumberFormat nf = NumberFormat.getNumberInstance(newLocale);
        displayInfoVO.setFormattedNumber(nf.format(defaultLongNumber));

        Date date = new Date();
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, newLocale);

        displayInfoVO.setDisplayDate(date);
        displayInfoVO.setFormattedDate(df.format(date));

        double amount = 1234567.12121;

        nf = NumberFormat.getCurrencyInstance(newLocale);

        displayInfoVO.setDisplayAmount(amount);
        displayInfoVO.setFormattedCurrency(nf.format(amount));

        return displayInfoVO;
    }

}
