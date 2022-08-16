package com.bill.service.subject;

import com.bill.service.observe.IObserveService;

public interface ISubjectService {
    void register(IObserveService service);
    void updateAllCache();
}
