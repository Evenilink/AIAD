package map_Exploration;

import sajas.core.Agent;
import sajas.core.behaviours.CyclicBehaviour;

public class MyAgent extends Agent {
    @Override
	public void setup() {
        System.out.println("Hello world!");
        this.addBehaviour(new MyBehaviour());
    }

    public void takeDown() {
        System.out.println("Bye... :(");
    }

    public class MyBehaviour extends CyclicBehaviour {
        private int n = 0;

        @Override
        public void action() {
            System.out.println(n++);
        }
    }
}