import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.*;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;


public class LinkNode extends RecursiveTask<Set<String>> {

    private final Set<String> useLink;
    private final String link;
    private static Containers container;

    public LinkNode(String root) {
        this.link = root.trim();
        this.useLink = Collections.synchronizedSet(new TreeSet<>());
        this.useLink.add(this.link);
        container = new Containers(this.link);
    }

    private LinkNode(String root, Set<String> set) {
        this.link = root.trim();
        this.useLink = set;
    }

    private synchronized void sleep() {

        try {
            Thread.sleep(125);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected Set<String> compute() {
        Set<String> children = getChildren(this.link);

        if (!children.isEmpty()) {
            List<LinkNode> taskList = new ArrayList<>();
            children.forEach(
                    fork_ -> {
                        if (useLink.add(fork_)) { //Если такой ссылки нет, то делаем новую задачу
                            LinkNode task = new LinkNode(fork_, useLink);
                            task.fork();
                            taskList.add(task);
                        }
                    });
            taskList.forEach(LinkNode::join);
        }
        return useLink;
    }


    public Set<String> getChildren(final String link_) {
        try {
            Document doc = Jsoup.connect(link_).ignoreHttpErrors(true).maxBodySize(0).timeout(0).get();
            sleep();
            return doc.select("a")
                    .stream()
                    .map(el -> el.attr("abs:href"))
                    .filter(el ->
                            el.contains(container.getROOT_URL()) &&
                                    !el.contains("#") &&
                                    el.endsWith("/")
                    ).collect(Collectors.toSet());
        } catch (Exception ex) {
            System.err.println(ex);
        }
        return Collections.emptySet();
    }

}
