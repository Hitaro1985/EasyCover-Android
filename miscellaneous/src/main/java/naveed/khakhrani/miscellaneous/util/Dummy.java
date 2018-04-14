package naveed.khakhrani.miscellaneous.util;

/**
 * Created by naveedali on 10/26/17.
 */

public class Dummy {

    public String name;
    public int dummyCount = 0;

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDummyCount() {
        return dummyCount;
    }

    public void setDummyCount(int dummyCount) {
        this.dummyCount = dummyCount;
    }
}
