package com.ycsys.smartmap.sys.common.config.parseobject.tomcat;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Created by Administrator on 2016/12/20.
 */
public class TomcatJVMStatusObject {
    private TomcatMemoryStatusObject memory;
    private List<TomcatMemoryPoolStatusObject> memorypools;

    @XmlElement(name = "memory")
    public TomcatMemoryStatusObject getMemory() {
        return memory;
    }

    public void setMemory(TomcatMemoryStatusObject memory) {
        this.memory = memory;
    }

    @XmlElement(name = "memorypool")
    public List<TomcatMemoryPoolStatusObject> getMemorypools() {
        return memorypools;
    }

    public void setMemorypools(List<TomcatMemoryPoolStatusObject> memorypools) {
        this.memorypools = memorypools;
    }
}
