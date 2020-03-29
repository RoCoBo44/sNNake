package snake;

public interface ActivationFunction {
	
	//esta clase es la inferfaz de sigmoid y relu, ademas permite poder agregar otras si es necesario

	abstract public Double transform(Double in);
	// aplica la funcion al numero dado
	
	abstract public Double Derivative (Double in);
	// aplica la derivada de la funcion al numero dado
}
