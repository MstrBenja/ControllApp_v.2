package controllApp.paquetes.menu.task;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class viewPagerAdapter extends FragmentStateAdapter {

    // constructor del adaptador de tabLayout
    public viewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    // método para ir cambiando entre fragmentos (to_do_task_Fragment y complete_tasks_Fragment)
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new to_do_task_Fragment();
            case 1: return new complete_tasks_Fragment();
            default: return new to_do_task_Fragment();
        }
    }

    // este método se ocupará para contabilizar la cantidad de tabs que existen
    @Override
    public int getItemCount() {
        return 2;
    }
}