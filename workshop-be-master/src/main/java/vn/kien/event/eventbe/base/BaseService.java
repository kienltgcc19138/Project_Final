package vn.kien.event.eventbe.base;

import org.springframework.beans.factory.annotation.Autowired;
import vn.kien.event.eventbe.repository.ISequenceValueItemRepository;
import vn.kien.event.eventbe.services.SequenceValueItemService;


public abstract class BaseService extends BaseObjectLoggable {

    @Autowired
    public SequenceValueItemService sequenceValueItemService;


}
