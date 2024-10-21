package org.burgas.employeeservice.mapper;

import lombok.RequiredArgsConstructor;
import org.burgas.employeeservice.dto.PositionRequest;
import org.burgas.employeeservice.dto.PositionResponse;
import org.burgas.employeeservice.entity.Position;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PositionMapper {

    public PositionResponse toPositionResponse(Position position) {
        return PositionResponse.builder()
                .id(position.getId())
                .name(position.getName())
                .build();
    }

    public Position toPosition(PositionRequest positionRequest) {
        return Position.builder()
                .id(positionRequest.getId())
                .name(positionRequest.getName())
                .build();
    }
}
