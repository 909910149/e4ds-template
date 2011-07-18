package ch.ralscha.e4ds.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.TREE_LOAD;

import java.io.IOException;
import java.util.Set;

import org.apache.commons.lang.mutable.MutableInt;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;

import com.google.common.collect.Sets;

@Service
public class NavigationService {

	private MenuNode root;

	public NavigationService() throws JsonParseException, JsonMappingException, IOException {
		Resource menu = new ClassPathResource("/menu.json");
		ObjectMapper mapper = new ObjectMapper();
		root = mapper.readValue(menu.getInputStream(), MenuNode.class);
	}

	@ExtDirectMethod(TREE_LOAD)
	@PreAuthorize("isAuthenticated()")
	public MenuNode getNavigation() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		MenuNode copyOfRoot = new MenuNode(root, authentication.getAuthorities());
		upateIdAndLeaf(new MutableInt(0), copyOfRoot);

		return copyOfRoot;
	}

	private void upateIdAndLeaf(MutableInt id, MenuNode parent) {
		parent.setId(id.intValue());
		id.add(1);

		parent.setLeaf(parent.getChildren().isEmpty());

		Set<MenuNode> removeChildren = Sets.newHashSet();
		for (MenuNode child : parent.getChildren()) {
			//Remove child if it has no children and it's not a leaf
			if (child.getView() == null && child.getChildren().isEmpty()) {
				removeChildren.add(child);
			} else {
				upateIdAndLeaf(id, child);
			}
		}

		parent.getChildren().removeAll(removeChildren);
	}

}
