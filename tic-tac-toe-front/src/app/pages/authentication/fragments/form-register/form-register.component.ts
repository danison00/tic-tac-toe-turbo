import { Component, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserRegisterDto } from 'src/app/dto/UserRegisterDto.interface';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-form-register',
  templateUrl: './form-register.component.html',
  styleUrls: ['./form-register.component.scss'],
})
export class FormRegisterComponent implements OnDestroy {
  protected formRegister: FormGroup;
  protected usernameUnavailableControl = false;
  modalRegisterSuccessfulControl = false;
  constructor(fb: FormBuilder, private userService: UserService) {
    this.formRegister = fb.group({
      name: [null, [Validators.minLength(5), Validators.required]],
      username: [null, [Validators.minLength(4), Validators.required]],
      password: [null, Validators.required],
    });
  }
  ngOnDestroy(): void {
    this.modalRegisterSuccessfulControl = false;
    this.formRegister.reset();
  }

  onRegister() {
    if (this.formRegister.invalid) return;

    const newUser: UserRegisterDto = {
      name: this.formRegister.get('name')?.value,
      username: this.formRegister.get('username')?.value,
      password: this.formRegister.get('password')?.value,
    };

    console.log(newUser);
    this.userService.register(newUser).subscribe({
      next: () => {
        this.modalRegisterSuccessfulControl = true;
      },
      error: () => {
        this.usernameUnavailableControl = true;
      },
    });

   
  }
}
