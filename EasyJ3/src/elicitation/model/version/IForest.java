package elicitation.model.version;

import java.util.List;

/**
 * 
 * @author John
 * 项目有多个场景，每个场景都有一棵版本树;从项目的粒度看，就形成一个森林.
 */
public interface IForest <E> {
	List<ITree<E>> getTrees();
}
