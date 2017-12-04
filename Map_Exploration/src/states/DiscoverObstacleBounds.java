package states;

import algorithms.pledge.Pledge;
import behaviours.Exploration;

public class DiscoverObstacleBounds implements IAgentTemporaryState {
    Exploration behaviour;
    Pledge pledge;

    @Override
    public void enter(Exploration behaviour) {
        this.behaviour = behaviour;
        this.pledge = this.behaviour.getPledge();
        this.pledge.init();
    }

    @Override
    public void execute() {
        this.pledge.run();
    }

    @Override
    public void exit() {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean canResume() {
        return this.pledge.hasFinished();
    }
}
