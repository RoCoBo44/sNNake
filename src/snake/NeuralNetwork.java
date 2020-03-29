package snake;


import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork{

	private List<Integer> NeuronsPerLayer;
	private List<Layer> Layers;
	private ActivationFunction AF;
	
	public NeuralNetwork(List<Integer> NeuronsPerLayer, ActivationFunction AF){
		
		//estos 2 rompen encap porque se puede acceder desde afuera
		this.NeuronsPerLayer = NeuronsPerLayer;
		this.AF = AF; 
		
		Layers = new ArrayList<Layer>();
		
		for (int i = 1 ;  i < NeuronsPerLayer.size(); i++){
			Layers.add(new Layer(this.NeuronsPerLayer.get(i-1),this.NeuronsPerLayer.get(i), AF));
		}
	}
	
	public Double[] Calculate(Double[] input){
		
		// Dado un input calculo cual es el output de la red
		
		if (input.length == this.NeuronsPerLayer.get(0)){
			Double[] Aux = input;
			for (int i = 0 ;  i < this.Layers.size() ; i++){
				Aux = Layers.get(i).Calculate(Aux);
			}
			return Aux;
		}
		System.out.println("Tamaños NO son correctos en NN");
		return null;
		
	}
	
	public void Train(Double[] input, Double[] solution, Double LearningRate ){
		
		if (input.length == this.NeuronsPerLayer.get(0) && solution.length == this.NeuronsPerLayer.get(this.NeuronsPerLayer.size()-1)){
			

			//calculo la salida
			Double[] output = this.Calculate(input);

			
			//saco la diferencia de la salida 
			Double[] Error = new Double[solution.length];
			for (int i = 0 ; i < solution.length ; i++){
				Error[i] =  ( solution[i] - output[i]) * AF.Derivative(output[i]); // VERIFICAR EL OUT;
			}
			//backpropagation 

			this.Layers.get(this.Layers.size()-1).SetError(Error);
			
			for (int i = this.Layers.size()-1 ; i > 0 ;i--){
				Error = this.Layers.get(i).GenerateError(Error);
				this.Layers.get(i-1).SetError(Error);
			}
			
			for (int i = this.Layers.size()-1 ; i >= 0 ;i--){
				this.Layers.get(i).SetWeights(LearningRate);
				this.Layers.get(i).SetBias(LearningRate);
			}
			
		}

	}
	
	
	public List<Integer> getNeurons(){
		List<Integer> Neurons = new ArrayList<Integer>();
		Neurons.addAll(NeuronsPerLayer);
		return Neurons;
	}
	
}

