package cs3500.animator.view.visualview;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import javax.swing.WindowConstants;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.view.AnimationView;

public class VisualView extends JFrame implements AnimationView {
  private static final int DEFAULT_WINDOW_WIDTH   = 800;
  private static final int DEFAULT_WINDOW_HEIGHT  = 600;
  private static final int DEFAULT_FRAME_TIME     = 16;

  @Override
  public void run(AnimationModel model, int tickRate) {
    AnimationPanel panel = new AnimationPanel(model, DEFAULT_FRAME_TIME,
            1000.0f / (float)tickRate);

    this.setTitle("EasyAnimator");
    this.setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    this.setLayout(new BorderLayout());
    panel.setPreferredSize(new Dimension(DEFAULT_WINDOW_WIDTH * 2, DEFAULT_WINDOW_HEIGHT * 2));
    this.add(panel, BorderLayout.CENTER);

    JScrollPane pane = new JScrollPane(panel);
    this.add(pane);

    this.setVisible(true);

    Timer timer = new Timer(DEFAULT_FRAME_TIME, panel);
    timer.start();

    while (true) {
      this.repaint();
    }
  }
}
