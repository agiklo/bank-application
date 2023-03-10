package pl.matcodem.bankacccmd.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
@AllArgsConstructor
public class CloseAccountCommand {

    @TargetAggregateIdentifier
    private String id;
}
