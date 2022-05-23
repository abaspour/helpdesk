package com.nicico.helpdesk.repository;


import com.nicico.helpdesk.model.Instruction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructionDAO extends JpaRepository<Instruction, Long>, JpaSpecificationExecutor<Instruction> {

}
