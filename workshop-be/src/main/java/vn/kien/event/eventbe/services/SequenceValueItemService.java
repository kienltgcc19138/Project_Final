package vn.kien.event.eventbe.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vn.kien.event.eventbe.entity.SequenceValueItem;
import vn.kien.event.eventbe.repository.ISequenceValueItemRepository;

import java.util.Date;


@Service
@RequiredArgsConstructor
public class SequenceValueItemService {
    private final ISequenceValueItemRepository sequenceValueItemRepository;
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public synchronized String getSequence(Class forClass) {
        String sequenceName = forClass.getName();
        SequenceValueItem sequenceValueItem = sequenceValueItemRepository.findBySeqName(sequenceName);
        if (null == sequenceValueItem) {
            sequenceValueItem = new SequenceValueItem();
            sequenceValueItem.setSeqName(sequenceName);
            sequenceValueItem.setSeqId(100);
            sequenceValueItem.setLastUpdatedStamp(new Date());
            sequenceValueItemRepository.save(sequenceValueItem);
            return "100";
        }
        int sequenceId = sequenceValueItem.getSeqId() + 1;
        sequenceValueItem.setSeqId(sequenceId);
        sequenceValueItem.setLastUpdatedStamp(new Date());
        sequenceValueItemRepository.save(sequenceValueItem);
        return String.valueOf(sequenceId);
    }
}
