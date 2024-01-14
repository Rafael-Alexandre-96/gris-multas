package br.com.gris.multas.domain.model;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class RegistroStatus {
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    /*private LocalDateTime deletedAt;*/

    public void setCreateAtNow() {
        if (createAt == null)
            this.createAt = LocalDateTime.now();
    }

    public void setUpdateAtNow() {
        this.updateAt = LocalDateTime.now();
    }
}
