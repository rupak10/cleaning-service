package com.app.service;

import com.app.dto.ListVo;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {

    public List<ListVo> getCleaningTypeList() {
        List<ListVo> list = new ArrayList<>();
        list.add(new ListVo("", "--select one--"));
        list.add(new ListVo("Normal Cleaning", "Normal Cleaning"));
        list.add(new ListVo("Deep Cleaning", "Deep Cleaning"));
        list.add(new ListVo("Kitchen Cleaning", "Kitchen Cleaning"));
        return list;
    }

    public List<ListVo> getServiceTypeList() {
        List<ListVo> list = new ArrayList<>();
        list.add(new ListVo("", "--select one--"));
        list.add(new ListVo("One Time", "One Time"));
        list.add(new ListVo("Two Weeks", "Two Weeks"));
        list.add(new ListVo("Monthly Cleaning", "Monthly Cleaning"));
        return list;
    }
}
