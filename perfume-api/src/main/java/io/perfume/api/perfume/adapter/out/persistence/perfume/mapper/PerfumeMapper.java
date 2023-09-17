package io.perfume.api.perfume.adapter.out.persistence.perfume.mapper;

import io.perfume.api.perfume.adapter.out.persistence.perfume.PerfumeJpaEntity;
import io.perfume.api.perfume.adapter.out.persistence.perfumeNote.NoteLevel;
import io.perfume.api.perfume.adapter.out.persistence.perfumeNote.PerfumeNoteEntity;
import io.perfume.api.perfume.domain.NotePyramidIds;
import io.perfume.api.perfume.domain.Perfume;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
public class PerfumeMapper {
  public Perfume toPerfume(PerfumeJpaEntity perfumeJpaEntity) {
    return Perfume.builder()
        .id(perfumeJpaEntity.getId())
        .name(perfumeJpaEntity.getName())
        .story(perfumeJpaEntity.getStory())
        .price(perfumeJpaEntity.getPrice())
        .concentration(perfumeJpaEntity.getConcentration())
        .capacity(perfumeJpaEntity.getCapacity())
        .brandId(perfumeJpaEntity.getBrandId())
        .deletedAt(perfumeJpaEntity.getDeletedAt())
        .updatedAt(perfumeJpaEntity.getUpdatedAt())
        .createdAt(perfumeJpaEntity.getCreatedAt())
        .build();
  }

  public PerfumeJpaEntity toPerfumeJpaEntity(Perfume perfume) {
    return PerfumeJpaEntity.builder()
        .id(perfume.getId())
        .name(perfume.getName())
        .story(perfume.getStory())
        .concentration(perfume.getConcentration())
        .price(perfume.getPrice())
        .capacity(perfume.getCapacity())
        .brandId(perfume.getBrandId())
        .thumbnailId(perfume.getThumbnailId())
        .build();
  }

  public List<PerfumeNoteEntity> toPerfumeNoteEntities(Long perfumeId,
                                                       NotePyramidIds notePyramidIds) {
    return Stream.of(
            toPerfumeNoteEntities(notePyramidIds.getTopNoteIds(), perfumeId, NoteLevel.TOP),
            toPerfumeNoteEntities(notePyramidIds.getMiddleNoteIds(), perfumeId, NoteLevel.MIDDLE),
            toPerfumeNoteEntities(notePyramidIds.getBaseNoteIds(), perfumeId, NoteLevel.BASE)
        )
        .flatMap(List::stream)
        .collect(Collectors.toList());
  }

  private List<PerfumeNoteEntity> toPerfumeNoteEntities(List<Long> noteIds, Long perfumeId,
                                                        NoteLevel noteLevel) {
    return noteIds.stream()
        .map(toPerfumeNoteEntity(perfumeId, noteLevel))
        .collect(Collectors.toList());
  }

  private Function<Long, PerfumeNoteEntity> toPerfumeNoteEntity(Long perfumeId,
                                                                NoteLevel noteLevel) {
    return noteId -> PerfumeNoteEntity.builder()
        .perfumeId(perfumeId)
        .noteId(noteId)
        .noteLevel(noteLevel)
        .build();
  }
}
