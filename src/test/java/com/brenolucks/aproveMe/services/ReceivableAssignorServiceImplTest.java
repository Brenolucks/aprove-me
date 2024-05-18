package com.brenolucks.aproveMe.services;

import com.brenolucks.aproveMe.domain.mapper.AssignorMapper;
import com.brenolucks.aproveMe.domain.mapper.ReceivableMapper;
import com.brenolucks.aproveMe.domain.model.Assignor;
import com.brenolucks.aproveMe.domain.model.Receivable;
import com.brenolucks.aproveMe.dto.ReceivableAndAssignorRequestDTO;
import com.brenolucks.aproveMe.dto.ReceivableAndAssignorResponseDTO;
import com.brenolucks.aproveMe.dto.assignor.AssignorRequestDTO;
import com.brenolucks.aproveMe.dto.assignor.AssignorResponseDTO;
import com.brenolucks.aproveMe.dto.receivable.ReceivableRequestDTO;
import com.brenolucks.aproveMe.dto.receivable.ReceivableResponseDTO;
import com.brenolucks.aproveMe.repositories.AssignorRepository;
import com.brenolucks.aproveMe.repositories.ReceivableRepository;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.action.internal.EntityActionVetoException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReceivableAssignorServiceImplTest {
    @Mock
    ReceivableRepository receivableRepository;
    @Mock
    AssignorRepository assignorRepository;
    @Mock
    ReceivableMapper receivableMapper;
    @Mock
    AssignorMapper assignorMapper;
    @Captor
    private ArgumentCaptor<Receivable> receivableArgumentCaptor;
    @Captor
    private ArgumentCaptor<Assignor> assignorArgumentCaptor;
    @InjectMocks
    ReceivableAssignorServiceImpl receivableAssignorService;
    private UUID receivableID;
    private UUID assignorID;

    @BeforeEach
    void setup(){
        receivableID = UUID.randomUUID();
        assignorID = UUID.randomUUID();
    }

    @Nested
    class registerReceivableAndAssignor{

        @Test
        @DisplayName("Should register a receivable and assignor with success")
        void shouldRegisterReceivableAndAssignorWithSuccess(){
            var receivable = new Receivable();
            var assignor = new Assignor();

            var receivableRequestDTO = new ReceivableRequestDTO(1000, new Date());
            var assignorRequestDTO = new AssignorRequestDTO("12345", "email@email.com", "123456789", "Email Test");
            var receivableAndAssignorRequestDTO = new ReceivableAndAssignorRequestDTO(receivableRequestDTO, assignorRequestDTO);

            var receivableResponseDTO = new ReceivableResponseDTO(1000, new Date());
            var assignorResponseDTO = new AssignorResponseDTO("12345", "email@email.com", "123456789", "Email Test");
            var receivableAndAssignorResponseDTO = new ReceivableAndAssignorResponseDTO(receivableResponseDTO, assignorResponseDTO);

            doReturn(assignor).when(assignorMapper).toAssignor(assignorRequestDTO);
            doReturn(assignor).when(assignorRepository).save(assignor);

            doReturn(receivable).when(receivableMapper).toReceivable(receivableRequestDTO);
            receivable.setId(assignorID);
            doReturn(receivable).when(receivableRepository).save(receivable);
            doReturn(receivableResponseDTO).when(receivableMapper).toReceivableResponseDTO(receivable);
            doReturn(assignorResponseDTO).when(assignorMapper).toAssignorResponseDTO(assignor);

            var output = receivableAssignorService.registerReceivableAndAssignor(receivableAndAssignorRequestDTO);

            Assertions.assertEquals(receivableAndAssignorResponseDTO, output);

            verify(assignorMapper, times(1)).toAssignorResponseDTO(assignor);
            verify(assignorRepository, times(1)).save(assignor);

            verify(receivableMapper, times(1)).toReceivable(receivableRequestDTO);
            verify(receivableRepository, times(1)).save(receivable);

            verify(receivableMapper, times(1)).toReceivableResponseDTO(receivable);
            verify(assignorMapper, times(1)).toAssignorResponseDTO(assignor);

            Assertions.assertEquals(receivableAndAssignorResponseDTO.assignorResponseDTO(), output.assignorResponseDTO());
            Assertions.assertEquals(receivableAndAssignorResponseDTO.receivableResponseDTO(), output.receivableResponseDTO());
        }

        @Test
        @DisplayName("Should generate an error when a request is null")
        void shouldShowErrorWhenRequestIsNull(){
            var receivable = new Receivable();
            var assignor = new Assignor();

            var receivableRequestDTO = new ReceivableRequestDTO(1000, new Date());
            var assignorRequestDTO = new AssignorRequestDTO("12345", "email@email.com", "123456789", "Email Test");

            var exceptionRequestReceivableAndAssignor =  Assertions.assertThrows(IllegalArgumentException.class,
                    () -> receivableAssignorService.registerReceivableAndAssignor(null));
            Assertions.assertEquals("Os campos do request não podem ser nulos.", exceptionRequestReceivableAndAssignor.getMessage());

            var exceptionRequestReceivable = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> receivableAssignorService.registerReceivableAndAssignor(new ReceivableAndAssignorRequestDTO(receivableRequestDTO, null)));
            Assertions.assertEquals("Os campos do request não podem ser nulos.", exceptionRequestReceivableAndAssignor.getMessage());

            var exceptionRequestAssignor = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> receivableAssignorService.registerReceivableAndAssignor(new ReceivableAndAssignorRequestDTO(null, assignorRequestDTO)));
            Assertions.assertEquals("Os campos do request não podem ser nulos.", exceptionRequestReceivableAndAssignor.getMessage());

            verify(receivableRepository, never()).save(receivable);
            verify(assignorRepository, never()).save(assignor);
        }
    }

    @Nested
    class registerReceivable {

        @Test
        @DisplayName("Should register a receivable with success")
        void shouldRegisterReceivableWithSuccess(){
            //Arrange
            var receivableRequestDTO = new ReceivableRequestDTO(3000, new Date());

            var receivable = new Receivable();
            receivable.setReceivableValue(receivableRequestDTO.receivableValue());
            receivable.setEmissionDate(receivableRequestDTO.emissionDate());

            var receivableResponseDTO = new ReceivableResponseDTO(
                    receivable.getReceivableValue(),
                    receivable.getEmissionDate()
            );

            doReturn(receivable).when(receivableMapper).toReceivable(receivableRequestDTO);
            doReturn(receivableResponseDTO).when(receivableMapper).toReceivableResponseDTO(receivable);
            doReturn(receivable).when(receivableRepository).save(receivableArgumentCaptor.capture());

            //Act
            var output = receivableAssignorService.registerReceivable(receivableRequestDTO);

            //Assert
            Assertions.assertNotNull(output);

            var receivableCaptured = receivableArgumentCaptor.getValue();
            Assertions.assertEquals(receivableCaptured.getReceivableValue(), output.receivableValue());
            Assertions.assertEquals(receivableCaptured.getEmissionDate(), output.emissionDate());
        }

        @Test
        @DisplayName("Should generate an error when trying register receivable with error")
        void shouldShowErrorWhenRegisterReceivableWithError(){
            var receivableRequestDTO = new ReceivableRequestDTO(1000, new Date());
            var receivable = new Receivable();
            receivable.setReceivableValue(receivableRequestDTO.receivableValue());
            receivable.setEmissionDate(receivableRequestDTO.emissionDate());

            doThrow(new RuntimeException("Error")).when(receivableMapper).toReceivable(receivableRequestDTO);

            Assertions.assertThrows(RuntimeException.class, () -> receivableAssignorService.registerReceivable(receivableRequestDTO));

            verify(receivableRepository, never()).save(receivable);
            verify(receivableMapper).toReceivable(receivableRequestDTO);
        }
    }

    @Nested
    class registerAssignor {

        @Test
        @DisplayName("Should register a assignor with success")
        void shouldRegisterAssignorWithSuccess(){
            //Arrange
            var assignorRequestDTO = new AssignorRequestDTO("PDF", "email@email.com", "88999999999", "test");

            var assignor = new Assignor();
            assignor.setDocument(assignorRequestDTO.document());
            assignor.setEmail(assignorRequestDTO.email());
            assignor.setPhone(assignorRequestDTO.phone());
            assignor.setName(assignorRequestDTO.name());

            var assignorResponseDTO = new AssignorResponseDTO(assignor.getDocument(), assignor.getEmail(), assignor.getPhone(), assignor.getName());

            doReturn(assignor).when(assignorMapper).toAssignor(assignorRequestDTO);
            doReturn(assignorResponseDTO).when(assignorMapper).toAssignorResponseDTO(assignor);
            doReturn(assignor).when(assignorRepository).save(assignorArgumentCaptor.capture());

            //Act
            var output = receivableAssignorService.registerAssignor(assignorRequestDTO);

            //Assert
            Assertions.assertNotNull(output);

            var assignorCaptured = assignorArgumentCaptor.getValue();
            Assertions.assertEquals(assignorCaptured.getDocument(), output.document());
            Assertions.assertEquals(assignorCaptured.getEmail(), output.email());
            Assertions.assertEquals(assignorCaptured.getPhone(), output.phone());
            Assertions.assertEquals(assignorCaptured.getName(), output.name());
        }

        @Test
        @DisplayName("Should generate an error when trying register assignor with error ")
        void shouldShowErrorWhenRegisterAssignorWithError(){
            var assignorRequestDTO = new AssignorRequestDTO("PDF", "teste", "teste", "teste");
            var assignor = new Assignor();
            assignor.setDocument(assignorRequestDTO.document());
            assignor.setEmail(assignorRequestDTO.email());
            assignor.setPhone(assignorRequestDTO.phone());
            assignor.setName(assignorRequestDTO.name());

            doThrow(new RuntimeException("Error")).when(assignorMapper).toAssignor(assignorRequestDTO);

            Assertions.assertThrows(RuntimeException.class, () -> receivableAssignorService.registerAssignor(assignorRequestDTO));

            verify(assignorRepository, never()).save(assignor);

            verify(assignorMapper).toAssignor(assignorRequestDTO);
        }
    }

    @Nested
    class deleteReceivable {

        @Test
        @DisplayName("Should delete a receivable with success")
        void shouldDeleteReceivableWithSuccess(){
            var receivable = new Receivable();

            doReturn(Optional.of(receivable)).when(receivableRepository).findReceivableById(receivableID);

            receivableAssignorService.deleteReceivable(receivableID);

            verify(receivableRepository, times(1)).findReceivableById(receivableID);
            verify(receivableRepository, times(1)).deleteById(receivableID);
        }

        @Test
        @DisplayName("Should generate an error when trying delete receivable with error")
        void shouldShowErrorWhenTryingDeleteReceivableWithError(){
            var receivable = new Receivable();

            doReturn(Optional.empty()).when(receivableRepository).findReceivableById(receivableID);

            Assertions.assertThrows(EntityNotFoundException.class, () -> receivableAssignorService.deleteReceivable(receivableID));

            verify(receivableRepository, times(1)).findReceivableById(receivableID);
            verify(receivableRepository, never()).deleteById(receivableID);

        }
    }

    @Nested
    class deleteAssignor{

        @Test
        @DisplayName("Should delete a assignor with success")
        void shouldDeleteAssignorWithSuccess(){
            var assignor = new Assignor();

            doReturn(Optional.of(assignor)).when(assignorRepository).findAssignorById(assignorID);

            receivableAssignorService.deleteAssignor(assignorID);

            verify(assignorRepository, times(1)).findAssignorById(assignorID);
            verify(assignorRepository, times(1)).deleteById(assignorID);
        }

        @Test
        @DisplayName("Should generate an error when trying delete assignor with error")
        void shouldShowErrorWhenTryingDeleteAssignorWithError(){
            var assignor = new Assignor();

            doReturn(Optional.empty()).when(assignorRepository).findAssignorById(assignorID);

            Assertions.assertThrows(EntityNotFoundException.class, () -> receivableAssignorService.deleteAssignor(assignorID));

            verify(assignorRepository, times(1)).findAssignorById(assignorID);
            verify(assignorRepository, never()).deleteById(assignorID);
        }
    }

    @Nested
    class updateReceivable{

        @Test
        @DisplayName("Should update an receivable with success")
        void shouldUpdateReceivableWithSuccess(){
            var receivable = new Receivable();
            var receivableRequestDTO = new ReceivableRequestDTO(300, new Date());
            var receivableResponseDTO = new ReceivableResponseDTO(300, new Date());

            doReturn(Optional.of(receivable)).when(receivableRepository).findReceivableById(receivableID);
            doReturn(receivableResponseDTO).when(receivableMapper).toReceivableResponseDTO(receivable);
            doReturn(receivable).when(receivableRepository).save(receivable);

            var output = receivableAssignorService.updateReceivable(receivableID, receivableRequestDTO);

            Assertions.assertEquals(receivableResponseDTO, output);

            verify(receivableRepository, times(1)).findReceivableById(receivableID);
            verify(receivableRepository, times(1)).save(receivable);

            Assertions.assertEquals(receivableResponseDTO.receivableValue(), output.receivableValue());
            Assertions.assertEquals(receivableResponseDTO.emissionDate(), output.emissionDate());
        }

        @Test
        @DisplayName("Should generate error when trying update an receivable with error")
        void shouldShowErrorWhenTryingUpdateReceivableWithError(){
            var receivable = new Receivable();
            var receivableRequestDTO = new ReceivableRequestDTO(300, new Date());

            doReturn(Optional.empty()).when(receivableRepository).findReceivableById(receivableID);

            var exception = Assertions.assertThrows(EntityNotFoundException.class, () -> receivableAssignorService.updateReceivable(receivableID, receivableRequestDTO));
            Assertions.assertEquals("Não foi encontrado nenhum recebível com este id: " + receivableID, exception.getMessage());

            verify(receivableRepository, times(1)).findReceivableById(receivableID);
            verify(receivableRepository, never()).save(receivable);


        }
    }

    @Nested
    class updateAssignor{

        @Test
        @DisplayName("Should update an assignor with success")
        void shouldUpdateAssignorWithSuccess(){
            var assignor = new Assignor();
            var assignorRequestDTO = new AssignorRequestDTO("12345678900", "test@example.com", "1234567890", "Test Name");
            var assignorResponseDTO = new AssignorResponseDTO("12345678900", "test@example.com", "1234567890", "Test Name");

            doReturn(Optional.of(assignor)).when(assignorRepository).findAssignorById(assignorID);
            doReturn(assignorResponseDTO).when(assignorMapper).toAssignorResponseDTO(assignor);
            doReturn(assignor).when(assignorRepository).save(assignor);

            AssignorResponseDTO output = receivableAssignorService.updateAssignor(assignorID, assignorRequestDTO);

            Assertions.assertEquals(assignorResponseDTO, output);

            verify(assignorRepository, times(1)).findAssignorById(assignorID);
            verify(assignorRepository, times(1)).save(assignor);

            Assertions.assertEquals(assignorResponseDTO.document(), output.document());
            Assertions.assertEquals(assignorResponseDTO.email(), output.email());
            Assertions.assertEquals(assignorResponseDTO.phone(), output.phone());
            Assertions.assertEquals(assignorResponseDTO.name(), output.name());
        }

        @Test
        @DisplayName("Should generate error when trying update an assignor with error")
        void shouldShowErrorWhenTryingUpdateAssignorWithError(){
            var assignor = new Assignor();
            var assignorRequestDTO = new AssignorRequestDTO("12345678900", "test@example.com", "1234567890", "Test Name");

            doReturn(Optional.empty()).when(assignorRepository).findAssignorById(assignorID);

            var exception = Assertions.assertThrows(EntityNotFoundException.class, () -> receivableAssignorService.updateAssignor(assignorID, assignorRequestDTO));

            Assertions.assertEquals("Não foi encontrado nenhum cedente com este id: " + assignorID, exception.getMessage());

            verify(assignorRepository, times(1)).findAssignorById(assignorID);
            verify(assignorRepository, never()).save(assignor);
        }
    }

    @Nested
    class getReceivableById{

        @Test
        @DisplayName("Should find an receivable by id")
        void shouldFindReceivableByID(){
            var receivable = new Receivable();
            receivable.setReceivableValue(3000);
            receivable.setEmissionDate(new Date());

            var receivableResponseDTO = new ReceivableResponseDTO(3000, new Date());

            doReturn(Optional.of(receivable)).when(receivableRepository).findReceivableById(receivableID);
            doReturn(receivableResponseDTO).when(receivableMapper).toReceivableResponseDTO(receivable);

            var output = receivableAssignorService.getReceivableById(receivableID);

            Assertions.assertEquals(receivableResponseDTO, output);

            verify(receivableRepository, times(1)).findReceivableById(receivableID);
            verify(receivableMapper, times(1)).toReceivableResponseDTO(receivable);

            Assertions.assertEquals(receivableResponseDTO.receivableValue(), output.receivableValue());
            Assertions.assertEquals(receivableResponseDTO.emissionDate(), output.emissionDate());
        }

        @Test
        @DisplayName("Should generate error with trying found an receivable with wrong ID")
        void shouldShowErrorWithTryingFoundReceivableWithWrongID(){
            doReturn(Optional.empty()).when(receivableRepository).findReceivableById(receivableID);

            var exception = Assertions.assertThrows(EntityNotFoundException.class, () -> receivableAssignorService.getReceivableById(receivableID));

            Assertions.assertEquals("Não existe recebível para este id: " + receivableID, exception.getMessage());

            verify(receivableRepository, times(1)).findReceivableById(receivableID);
        }
    }

    @Nested
    class getAssignorById{

        @Test
        @DisplayName("Should find an assignor by id")
        void shouldFoundAnAssignorByID(){
            var assignor = new Assignor();
            assignor.setDocument("12345");
            assignor.setEmail("email@email.com");
            assignor.setPhone("123456789");
            assignor.setName("Test name");

            var assignorResponseDTO = new AssignorResponseDTO("12345", "email@email.com", "123456789", "Test name");

            doReturn(Optional.of(assignor)).when(assignorRepository).findAssignorById(assignorID);
            doReturn(assignorResponseDTO).when(assignorMapper).toAssignorResponseDTO(assignor);

            var output = receivableAssignorService.getAssignorById(assignorID);
            Assertions.assertEquals(assignorResponseDTO, output);

            verify(assignorRepository, times(1)).findAssignorById(assignorID);
            verify(assignorMapper, times(1)).toAssignorResponseDTO(assignor);

            Assertions.assertEquals(assignorResponseDTO.document(), output.document());
            Assertions.assertEquals(assignorResponseDTO.email(), output.email());
            Assertions.assertEquals(assignorResponseDTO.phone(), output.phone());
            Assertions.assertEquals(assignorResponseDTO.name(), output.name());
        }

        @Test
        @DisplayName("Should generate error with trying found an assignor with wrong ID")
        void shouldShowErrorWithTryingFoundAssignorWithWrongID(){
            doReturn(Optional.empty()).when(assignorRepository).findAssignorById(assignorID);

            var exception = Assertions.assertThrows(EntityNotFoundException.class, () -> receivableAssignorService.getAssignorById(assignorID));

            Assertions.assertEquals("Não existe cedente para este id: " + assignorID, exception.getMessage());

            verify(assignorRepository, times(1)).findAssignorById(assignorID);
        }
    }

}