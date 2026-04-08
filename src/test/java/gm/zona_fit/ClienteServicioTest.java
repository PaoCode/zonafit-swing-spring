package gm.zona_fit;

import gm.zona_fit.modelo.Cliente;
import gm.zona_fit.repositorio.ClienteRepositorio;
import gm.zona_fit.servicio.ClienteServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ClienteServicioTest {
    @Mock
    private ClienteRepositorio  clienteRepositorio;

    @InjectMocks
    private ClienteServicio clienteServicio;

    @BeforeEach
    void seUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarClientes(){
        //Arrange
        Cliente cliente1 = new Cliente(1,"Paola", "Prado",100);
        Cliente cliente2 = new Cliente (2,"Raul", "Torres",200);
        when(clienteRepositorio.findAll()).thenReturn(Arrays.asList(cliente1,cliente2));

        //Act
        List<Cliente> clientes = clienteServicio.listarClientes();

        //Assert
        assertEquals(2,clientes.size());
        verify(clienteRepositorio, times(1)).findAll();
    }

    @Test
    void testBuscarClientePorId(){
        //Arrange
        Cliente clienteId = new Cliente(3,"Susy","Lopez",300);
        when(clienteRepositorio.findById(3)).thenReturn(Optional.of(clienteId));

        //Act
        Cliente cliente = clienteServicio.buscarClientePorId(3);

        //Assert
        assertNotNull(cliente);
        assertEquals("Susy",cliente.getNombre());
    }

    @Test
    void testAgregarCliente(){
        //Arrange
        Cliente clienteAdd = new Cliente(4,"Kategiux","Torres",400);
        when(clienteRepositorio.save(any(Cliente.class))).thenReturn(clienteAdd);

        //Act
        Cliente clienteAgregado = clienteServicio.guardarCliente(clienteAdd);

        //Assert
        assertNotNull(clienteAgregado);
        assertEquals("Kategiux",clienteAgregado.getNombre());
        assertEquals(4,clienteAgregado.getId());
        verify(clienteRepositorio, times(1)).save(clienteAgregado);
    }

    @Test
    void testEliminarCliente(){
        //Arrange
        Cliente clienteEliminar = new Cliente(1,"Paola","Prado",100);
        when(clienteRepositorio.findById(1)).thenReturn(Optional.of(clienteEliminar));

        doNothing().when(clienteRepositorio).delete(clienteEliminar);

        //Act
        clienteServicio.eliminarCliente(clienteEliminar);

        //Assert
        verify(clienteRepositorio).delete(clienteEliminar);

    }
}
