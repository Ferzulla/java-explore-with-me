package ru.practicum.explore.event.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.explore.enums.StateActionUser;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventUpdateRequestUser extends EventUpdateDto {
     StateActionUser stateAction;
}
