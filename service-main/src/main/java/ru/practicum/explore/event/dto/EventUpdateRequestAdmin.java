package ru.practicum.explore.event.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.explore.enums.StateActionAdmin;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventUpdateRequestAdmin extends EventUpdateDto {
     StateActionAdmin stateAction;
}
