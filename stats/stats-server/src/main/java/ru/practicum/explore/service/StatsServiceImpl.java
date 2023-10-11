package ru.practicum.explore.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.explore.model.ModelHit;
import ru.practicum.explore.repository.StatsRepository;
import ru.practicum.explore.useDto.dto.HitDto;
import ru.practicum.explore.useDto.dto.StatsDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static ru.practicum.explore.model.ModelMapper.toModelHit;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatsServiceImpl implements StatsService {

    final StatsRepository statsRepository;
    final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String GetStats = "Дата начала {} должна быть ранее даты окончания {}.";

    @Autowired
    public StatsServiceImpl(StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }


    @Override
    public ModelHit saveHit(HitDto hitDto) {
        ModelHit newModelHit = toModelHit(hitDto);
        return statsRepository.save(newModelHit);
    }

    @Override
    public List<StatsDto> getStats(String start, String end, List<String> uris, boolean unique) {

        LocalDateTime startTime = LocalDateTime.parse(start, dateTimeFormatter);
        LocalDateTime endTime = LocalDateTime.parse(end, dateTimeFormatter);
        if (startTime.isAfter(endTime)) {
            log.warn(GetStats, startTime, endTime);
            throw new IllegalArgumentException(GetStats
                    + startTime + endTime);
        }

        List<StatsDto> hits;
        if (unique) {
            hits = getUniqueHits(startTime, endTime, uris);
        } else {
            hits = getAllHits(startTime, endTime, uris);
        }
        return hits;
    }


    private List<StatsDto> getUniqueHits(LocalDateTime start, LocalDateTime end, List<String> uris) {
        List<StatsDto> hits;
        if (uris == null) {
            log.info("Uris is null");
            hits = statsRepository.findAllUniqueIp(start, end);
            ;
        } else {
            log.info("Uris : {}", uris);
            hits = statsRepository.findStatsByUrisByUniqueIp(start, end, uris);
        }
        return hits;
    }

    private List<StatsDto> getAllHits(LocalDateTime start, LocalDateTime end, List<String> uris) {
        List<StatsDto> hits;
        if (uris == null) {
            log.info("Uris is null");
            hits = statsRepository.findAll(start, end);
        } else {
            log.info("Uris: {}", uris);
            hits = statsRepository.findAllByUris(start, end, uris);
        }
        return hits;
    }

}